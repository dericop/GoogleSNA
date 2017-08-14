import pickle
import requests
import json

headers = {'Authorization': 'token e06ed368f9f33e17a713062b1da5ed83b018b1b4'}
org = 'google'

with open("google_repos_names.file", "rb") as f:
    repos = pickle.load(f)

# Map with pairs of the form { committer_login : no_of_commits }
committers = {}

for repo in repos:
	print(repo)
	cpage = 1
	url = 'https://api.github.com/repos/'+org+'/'+repo+'/commits?page='+str(cpage)
	r = requests.get(url, headers=headers)	
	r_object = r.json()
	for commit in r_object:
		if commit['author']:
			if commit['author']['login']:
				if commit['author']['login'] not in committers:
					committers[commit['author']['login']] = 1
				else:
					committers[commit['author']['login']] += 1
		if commit['committer']:
			if commit['committer']['login']:
				if not commit['author'] or not commit['author']['login'] or commit['committer']['login'] != commit['author']['login']:
					if commit['committer']['login'] not in committers:
						committers[commit['committer']['login']] = 1
					else:
						committers[commit['committer']['login']] += 1

	while len(r_object) > 0 and cpage < 10:
		print("length: "+str(len(r_object)))
		print("page: "+str(cpage))
		cpage += 1
		url = 'https://api.github.com/repos/'+org+'/'+repo+'/commits?page='+str(cpage)
		r = requests.get(url, headers=headers)
		r_object = r.json()
		for commit in r_object:
			if commit['author']:
				if commit['author']['login']:
					if commit['author']['login'] not in committers:
						committers[commit['author']['login']] = 1
					else:
						committers[commit['author']['login']] += 1
			if commit['committer']:
				if commit['committer']['login']:
					if not commit['author'] or not commit['author']['login'] or commit['committer']['login'] != commit['author']['login']:
						if commit['committer']['login'] not in committers:
							committers[commit['committer']['login']] = 1
						else:
							committers[commit['committer']['login']] += 1


for committer in committers:
	print(committer+" : "+str(committers[committer]))

with open("google_committers_logins.file", "wb") as f:
	pickle.dump(committers, f, pickle.HIGHEST_PROTOCOL)