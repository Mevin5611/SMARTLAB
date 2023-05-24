import threading
import time
from DBConnection import Db
class processkillthread (threading.Thread):
    def __init__(self):
       threading.Thread.__init__(self)
       # self.sid=sid
    def run(self):
        while(1):
            print("==============================")
            qrr = "SELECT * FROM `blockedapps`"
            c=Db()
            res= c.select(qrr)
            lis=[]
            for i in res:
                lis.append(i['app_name'])
            print(lis,"nnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn")
            import psutil

            for proc in psutil.process_iter():
                print(proc.name())
                try:

                    processName = proc.name()
                    processID = proc.pid

                    if processName in lis:
                        import psutil
                        PROCNAME = processName
                        for proc in psutil.process_iter():
                            print(proc.name(),"llll")
                            if proc.name() == PROCNAME:
                                print("ok")
                                proc.kill()


                except (psutil.NoSuchProcess, psutil.AccessDenied, psutil.ZombieProcess):
                    print("ooooooooooooooooo")
                    pass


            print('haiii')


def getmacaddress():
    import uuid
    # print("The MAC address in formatted way is : ", end="")
    k = (':'.join(['{:02x}'.format((uuid.getnode() >> ele) & 0xff) for ele in range(0, 8 * 6, 8)][::-1]))
    return k

macid = getmacaddress()
print(macid)


keythread = processkillthread()
keythread.run()



