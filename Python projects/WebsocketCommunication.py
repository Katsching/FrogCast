import random

import stomper
from websocket import create_connection


class MSG(object):
    def __init__(self, msg):
        self.msg = msg
        sp = self.msg.split("\n")
        self.destination = sp[1].split(":")[1]
        self.content = sp[2].split(":")[1]
        self.subs = sp[3].split(":")[1]
        self.id = sp[4].split(":")[1]
        self.len = sp[5].split(":")[1]
        # sp[6] is just a \n
        self.message = ''.join(sp[7:])[0:-1]  # take the last part of the message minus the last character which is \00



ws = create_connection("ws://localhost:8080/my-ws-app")
ws.send("CONNECT\naccept-version:1.0,1.1,2.0\n\n\x00\n")
v = str(random.randint(0, 1000))
sub = stomper.subscribe("/topic/messages", v, ack='auto')
ws.send(sub)
d = ws.recv()
print(d)

