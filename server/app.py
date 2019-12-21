from flask import Flask, Response
from flask import request, jsonify
import uuid

app = Flask(__name__)

users = []
potholes = []
freshness = 0

def get_or_create(lat, long):
    exists = next((item for item in potholes if item['lat'] == lat and item['long'] == long), None)
    if exists==None:
        pid = str(uuid.uuid1())
        potholes.append({
            'lat':request.json['lat'],
            'long':request. json['long'],
            'pid': pid,
        })
    else:
        pid = exists['pid']
    return pid

@app.route("/")
def index():
    return Response("It works!"), 200

@app.route("/hello")
def hello():
    return Response("Hello my friend"), 200

@app.route('/api/v1/start', methods=['GET'])
def api_start():
    print('Start')
    if 'id' in request.args:
        id = int(request.args['id'])

        this_user = next((item for item in users if item['id'] == id), None)
        if this_user==None:
            users.append({
                'id':id,
                'collection':[],
                'potholes': []
            })
            return jsonify(users[-1])
        else:
                # Make potholes = [] of that id
            this_user['potholes'] = []
            return jsonify(this_user)
        
    else:
        return "Error: No id field provided. Please specify an id."

@app.route('/api/v1/potholes', methods=['GET'])
def api_get_potholes():
    print('Get Potholes')
    global freshness
    if 'id' in request.args:
        id = int(request.args['id'])

        this_user = next((item for item in users if item['id'] == id), None)
        if this_user == None:
            return "Error: No user with id %d. Please specify a valid id."%(id)
        
        if (freshness==0):
            outs = []
        else:
            outs = this_user['potholes'][-freshness]
        freshness = 0
        return jsonify(outs)
    else:
        return "Error: No id field provided. Please specify an id."


@app.route('/api/v1/potholes', methods=['POST'])
def api_add_pothole():
    print('Pothole Detected')
    global freshness
    print(request.get_json(force=True))
    if not (request.json and  'lat' in request.json and 'long' in request.json and 'id' in request.json):
        print(request.json)
        return "Error: Invalid params"
    id = int(request.json['id'])
    this_user = next((item for item in users if item['id'] == id), None)
    if this_user == None:
        return "Error: No user with id %d. Please specify a valid id."%(id)
    pid = get_or_create(request.json['lat'], request.json['long'])
    this_user['potholes'].append({
        'lat':request.json['lat'],
        'long':request. json['long'],
        'pid': pid,
        'resp': None
    })
    freshness += 1
    return "Successfully added pothole %s"%(pid)

@app.route('/api/v1/review', methods=['POST'])
def api_review_pothole():
    print('Review from the user')
    if not request.json or not 'pid' in request.json or not 'resp' in request.json or not 'id' in request.json:
        print(request.json)
        return "Error: Invalid params"
    id = int(request.json['id'])
    this_user = next((item for item in users if item['id'] == id), None)
    if this_user == None:
        return "Error: No user with id %d. Please specify a valid id."%(id)
    pid = str(request.json['pid'])
    print(pid)
    user_potholes  = this_user['potholes']
    print(user_potholes)
    hole = next((item for item in user_potholes if item['pid'] == pid), None)
    if hole == None:
        return "Error: No pothole with pid %s. Please specify a valid pid."%(pid)
    hole['resp'] = bool(int(request.json['resp']))
    return "Successfully recorded response"

@app.route('/api/v1/gameholes', methods=['GET'])
def api_game_potholes():
    print('Fetching for the game')
    if 'id' in request.args:
        id = int(request.args['id'])

        this_user = next((item for item in users if item['id'] == id), None)
        if this_user == None:
            return "Error: No user with id %d. Please specify a valid id."%(id)
        game_potholes = [h for h in this_user['potholes'] if h['resp']==True ]
        return jsonify(game_potholes)
        
    else:
        return "Error: No id field provided. Please specify an id."

@app.route('/api/v1/result', methods=['POST'])
def api_result():
    print('Got results of game')
    if not request.json or not 'id' in request.json or not 'reward' in request.json:
        print(request.json)
        return "Error: Invalid params"
    id = int(request.json['id'])
    print(request.json)
    this_user = next((item for item in users if item['id'] == id), None)
    if this_user == None:
        return "Error: No user with id %d. Please specify a valid id."%(id)
    reward = int(request.json['reward'])
    if reward==0:
        print("Lost the game, no rewards")
        return jsonify(this_user['collection'])
    elif reward<5:
        this_user['collection'].append(reward)
        return jsonify(this_user['collection'])
    else:
        print("Invalid Reward")
        return "Invalid Reward"

@app.route('/api/v1/info', methods=['GET'])
def api_info():
    #mock =  {"collection":[1,2]}
    #mock = [1,2]
    #return jsonify(mock)
    if 'id' in request.args:
        id = int(request.args['id'])

        this_user = next((item for item in users if item['id'] == id), None)

        if this_user==None:
            return "Error: No user with id %d. Please specify a valid id."%(id)
        else:
            mock = {"collection":this_user['collection']}
            return jsonify(mock)

    else:
        return "Error: No id field provided. Please specify an id."


    
if __name__ == "__main__":
    app.debug = True
    freshness = 0
    users = []
    #app.run(host='10.70.26.227',port=5000)
    app.run(host = '0.0.0.0',port=8000)
