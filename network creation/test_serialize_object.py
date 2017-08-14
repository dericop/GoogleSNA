import pickle
import requests
import json

with open("request_result.file", "rb") as f:
    result = pickle.load(f)

google_repos = result.json()


#print(google_repos['data']['organization']['repositories']['nodes'][0]['name'])
print(o['data']['organization']['repositories']['nodes'][0]['mentionableUsers']['nodes'][0]['login'])