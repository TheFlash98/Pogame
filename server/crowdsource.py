from math import floor
import numpy as np
import random

class crowdsource(object):

	def __init__(self,r=10,m_init=500,n_init=500):

		self.A = np.zeros(m_init,n_init)
		self.X = np.zeros(m_init,n_init)
		self.Y = np.zeros(m_init,n_init)
		self.m = m_init
		self.n = n_init
		self.r = r
		self.l = floor(n_init*r/m_init)		
		self.E = []


	def taskmaster(self):
		"""
		Distribute tasks
		construct biparite graph to assign tasks
		ml=nr
		"""
		self.task_master = np.zeros(self.m,self.n)
		total_task = self.m*self.l
		task = [i for i in range(total_task)]
		total_worker = self.n*self.r
		worker = [i for i in range(total_worker)]
	
		for x in range(total_task):

			a = random.choice(task)
			b = random.choice(worker)
			i = a//l
			j = b//r
			self.task_master[i][j] = 1
			task.remove(a)
			worker.remove(b)
			self.E.append([i,j])

		return self.task_master


	def answers(self,A):
		"""
		enters the response of workers
		"""

`		self.A = A
	
	def inference(self,maxiters=200):
		"""
		message passing
		"""
		self.X = np.zeros(self.m,self.n)
		self.Y = np.random.normal(1,1,(self.m,self.n))
		
		for iter in range(maxiters):

			for e in self.E:
				
				i = e[0]
				j = e[1]
				
				index = taskmaster[i,:] == 1
				y_ij = self.Y[i,:][index] 
				Aij =  self.A[i,:][index]
				self.X[i][j] = sum(np.multiply(Aij,y_ij)) - self.A[i][j]*self.Y[i][j]

			for e in self.E:
				
				i = e[0]
				j = e[1]
				
				index = taskmaster[:,j] == 1
				x_ij = self.X[:,j][index] 
				Aij =  self.A[:,j][index]
				self.Y[i][j] = sum(np.multiply(Aij,x_ij)) - self.A[i][j]*self.X[i][j]

		print("Inference done")

	
	
	def task_decision(self):
		
		decisions = np.sum(np.multiply(self.Y,self.A),1)
		return decisions



	def reliability(self):

		return np.mean(self.Y,0)

		

	
	





