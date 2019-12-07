import os
songListName = "Classic"
songlist = os.listdir(songListName)
count = 0
for song in songlist:
	f = open(songListName + "/" + song)
	Artist = song.split("(")[0]
	songName = song.split("(")[1]
	songName = songName[:-5]
	g = open("Assets/"+songListName+"/"+str(count)+".txt","w+")
	jsonstring = ""
	line = f.readline()
	jsonstring += "{\"id\":"+ str(count) +","
	jsonstring += "\"artist\":" + "\""+Artist+"\",\"name\":" + "\""+songName+"\","
	jsonstring += "\"lyrics\":["
	while line:
		line = line.rstrip('\n')
		jsonstring +="\"%s\"," %line
		line = f.readline()
	jsonstring = jsonstring[:-1]
	jsonstring += "]}"
	g.write(jsonstring)
	f.close()
	count = count+ 1