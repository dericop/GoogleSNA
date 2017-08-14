import pickle
import requests
import json
import networkx as nx
import matplotlib.pyplot as plt

with open("request_result.file", "rb") as f:
    result = pickle.load(f)

with open("google_committers_logins.file", "rb") as g:
	commits = pickle.load(g)

google_repos = result.json()

reps = google_repos['data']['organization']['repositories']['nodes']

node_id = 1

# Map holding { user_login : { name, commits, company, node_id } }
users = {}

# Map containing { repository_name : { node_id } } 
repos = {}

G = nx.Graph()

for rep in reps:
	print(rep['name'])
	#repos[rep['name']] = { 'node_id' : node_id }
	#G.add_node(node_id, label=rep['name'], color='')#, type='repository')
	#node_id += 1
	people = rep['mentionableUsers']['nodes']
	for person in people:
		if person['login'] not in users:
			c_n = 0
			if person['login'] in commits:
				c_n = commits[person['login']]
			users[person['login']] = { 'name' : person['name'], 'commits' : c_n, 'company' : person['company'], 'node_id' : node_id}
			G.add_node(node_id, label=person['login'])#, type='user', name=person['name'], commits=c_n, company=person['company'])
			node_id += 1
	print('\n')

for rep in reps:
	people = rep['mentionableUsers']['nodes']
	for i in range(len(people)):
		p1 = people[i]
		u1_id = users[p1['login']]['node_id']
		for j in range(i+1, len(people)):
			p2 = people[j]
			u2_id = users[p2['login']]['node_id']
			if G.has_edge(u1_id, u2_id):
				G.edge[u1_id][u2_id]['weight'] += 1
			else:
				G.add_edge(u1_id, u2_id, weight = 1)

nx.write_gexf(G, 'test2.gexf')