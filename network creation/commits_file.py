import pickle

with open("commits_result.file", "rb") as f:
    result = pickle.load(f)

for commit_author in result:
	print(commit_author)

#print(result[0]['author']['login'])