#! /usr/bin/env python

import monitorrestclient, sys

printtrace = True

def applicationdataok(data):
    lines = data.split('\n')
    for a in lines:
        if a.find(': ') == -1:
            print "can not create application from: '", data, "'"
            return False
    if len(lines) < 4:
        print "Four lines are needed. Can not create application from: '", data, "'"
        return False
    return True



def get_applications(rest, sessionsArg, option):

    """ Converts this output from get_all_applications into a list of Application instances
    <
    sessionID............: 0
    nameInEnvironmentView: test application
    fileName.............: applications/test-application-with-long-name-001.txt
    ps -ef | grep javself...: eclipse
    and stdout '1' from..: ls /var/log/syslog | wc -w
    and stdout '1' from..: grep -i run-parts /etc/crontab | wc -w
    """
    applications = []
    if option == 'heartbeat':
        appsString = rest.get_environment_applications(sessionsArg).strip();
    else:
        appsString = rest.get_all_applications().strip();
    rawList = appsString.split('\n<\n')
    for raw in rawList:
        if printtrace: print '_' * 20
        if applicationdataok(raw):
            attributes = [a.split(': ')[1] for a in raw.split('\n')]
            if printtrace: print attributes

            a = Application()
            a.sessionId = attributes[0]
            a.nameInEnvironmentView = attributes[1]
            a.possibleName = a.nameInEnvironmentView
            a.fileName = attributes[2]
            a.processString = attributes[3]
            a.discoveryChecks = attributes[4:]
            a.unsubstitutedDiscoveryChecks = list(a.discoveryChecks)
            # x010-generic-bst-var-log-001.txt is not generic 
            a.isgeneric = a.fileName.find('generic-application') > 0
            if not a.isgeneric:
                applications.append(a)
    return applications

class Application:

    "Information needed to discover an application"
    sessionId = 0
    nameInEnvironmentView = ''
    possibleName = ''
    fileName = ''
    processString = ''
    discoveryChecks = []
    unsubstitutedDiscoveryChecks = []

    def __init__(self):
        pass

    def toList(self):
        return [self.sessionId, self.nameInEnvironmentView, self.fileName, self.processString, self.discoveryChecks]

    def matches(self, rest, proc, sessionId):
        self.nameIfMatched = self.nameInEnvironmentView
        #self.discoveryChecks = self.unsubstitutedDiscoveryChecks
        for i in range(len(self.unsubstitutedDiscoveryChecks)):
            self.discoveryChecks[i] = self.unsubstitutedDiscoveryChecks[i]
        self.possibleName = ''
        if printtrace: print "application.py cheching", self.nameInEnvironmentView
        if self.isgeneric:
            return False
        if self.nameInEnvironmentView.find('unknown ') == 0:
            self.possibleName = self.extract_name(proc)
        if self.possibleName != '':
            print 'name of unknown application is:', self.possibleName
            self.nameIfMatched = self.possibleName
            for i in range(len(self.discoveryChecks)):
                self.discoveryChecks[i] = self.discoveryChecks[i].replace('${applicationName}', self.possibleName)
                if printtrace: print 'replaced ${applicationName} in discoveryCheck', i, self.discoveryChecks[i]
        positioninproc = proc.find(self.processString)
        if positioninproc == -1:
            if printtrace: print "did not find '" + self.processString + "' in '" + proc
            return False
        for command in self.discoveryChecks:
            result = rest.execute_command(sessionId, command)
            if printtrace: print "'" + command + "' returned: " + result.strip()
            if not result.strip() == '1':
                print 'return value was not 1'
                return False
        return True

    def extract_name(self, proc):
        extractedName = ''
        logDirStart = proc.find("log.dir=/var/log/")
        if logDirStart > 0:
            logDirEnd = logDirStart + len("log.dir=/var/log/")
            nextSpace = proc.find(" ", logDirEnd)
            extractedName = proc[logDirEnd:nextSpace]
            return extractedName
        logDirStart = proc.find("log.dir=/var/app/")
        if logDirStart > 0:
            logDirEnd = logDirStart + len("log.dir=/var/app/")
            nextSpace = proc.find("tomcat/logs ", logDirEnd)
            extractedName = proc[logDirEnd:nextSpace]
        return extractedName
