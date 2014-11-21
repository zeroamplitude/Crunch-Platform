from py4j.java_gateway import JavaGateway
from time import sleep
from sys import stdin, exit
import pygame
import math
import thread

from PodSixNet.Connection import connection, ConnectionListener


class CramClient(ConnectionListener):
    def __init__(self, host, prt):
        """
        " Setup Java Python connection
        """
        self.version = 1
        self.leaders = []
        self.gateway = JavaGateway(auto_convert=True)
        self.pyva = self.gateway.entry_point.player2()

        """
        " Connect to Server
        """
        self.Connect((host, prt))
        print "Welcome to the Crunch-Platform client portal"

        """
        " << Enter your  team name when prompted >>
        "  ** Please limit name to 4 characters **
        "   *        to keep gui clean          *
        """
        self.teamname = self.pyva.getTeamname()
        connection.Send({"action": "teamname",
                         "teamname": self.teamname,
                         "ingame": False,
                         "version": self.version})
        print "Connecting to Crunch-Platform..."

        self.timer = 90
        self.clock = pygame.time.Clock()
        self.clock.tick(60)

        """
        " Initializing the console
        """
        self.initGraphics()
        pygame.init()
        pygame.font.init()
        width, height = 600, 500
        self.screen = pygame.display.set_mode((width, height))
        pygame.display.set_caption("Cram Game - " + self.teamname)

        """
        " Initialize game settings
        """
        self.board = [[False for x in range(5)] for y in range(5)]
        self.owner = [[None for x in range(5)] for y in range(5)]
        self.turn = False
        self.playerID = None
        self.gameID = None

        self.me = 0
        self.opponent = 0
        self.didiwin = False
        self.isgameover = False
        self.ismoveready = False

        self.tournamentMode = False
        self.roUnd = 0;

        self.playagain = False
        self.selected = False
        self.playerselect = False
        self.tBegin = False
        self.begingame = False

        self.endsession = False
        self.mainMenu()

    #################################
    ###     Game options menu     ###
    #################################

    def mainMenu(self):
        """
        " Select game type:
        "    Player Vs. Bot - Server bots
        "    Player Vs. Player - Play against opponent
        " << use your mouse >>
        """
        while not self.selected:
            self.selectRoom()

        """
        " Select client:
        "   If Player Vs. Player option was selected
        "   select your opponent from online players
        " << use your mouse >>
        """
        while not self.playerselect:
            self.selectPlayer()

        while not self.tBegin:
            self.tWaiting()

        while not self.begingame:
            self.Pump()
            connection.Pump()

        """
        " Set game instance client roles
        """
        if self.playerID == 0:
            self.turn = True
            self.marker = self.greenplayer
            self.othermarker = self.blueplayer
        else:
            self.turn = False
            self.marker = self.blueplayer
            self.othermarker = self.greenplayer

        pygame.display.flip()

    def selectRoom(self):
        self.screen.fill(0)
        self.drawSelectScreen()
        pygame.display.flip()
        connection.Pump()
        self.Pump()

        for event in pygame.event.get():
            # quit id the button is pressed
            if event.type == pygame.QUIT:
                exit()
        mouse = pygame.mouse.get_pos()

        xpos = int(mouse[0])
        ypos = int(mouse[1])

        if pygame.mouse.get_pressed()[0]:
            if 200 < xpos < 250:
                connection.Send({"action": "getPlayers",
                                 "teamname": self.teamname})
            if 100 < xpos < 150:
                connection.Send({"action": "botplay",
                                 "teamname": self.teamname})
            if 160 < ypos < 210 and 125 < xpos < 175:
                connection.Send({"action": "tournament", "teamname": self.teamname,
                                 "round": self.roUnd, "WorL": None, "score": None})
                sleep(1)


    def drawSelectScreen(self):
        # self.screen.blit(self.home, (0, 0))
        self.screen.blit(self.gameroom, (0, 0))
        self.screen.blit(self.leaderboard, (389, 0))
        #self.screen.blit(self.botimg, (100, 100))
        self.screen.blit(self.greenplayer, (200, 100))
        self.screen.blit(self.tournament, (125, 160))

    def selectPlayer(self):
        connection.Pump()
        self.Pump()
        try:
            connection.Send({"action": "getPlayers", "teamname": self.teamname})
        except:
            pass
        i = 0
        pBoard = [[False for x in range(6)] for y in range(7)]
        for x in range(6):
            for y in range(7):
                if len(self.teams) <= i:
                    pBoard[y][x] = True
                i += 1

        self.screen.fill(0)
        self.drawPlayerboard()
        pygame.display.flip()

        for event in pygame.event.get():
            # quit id the button is pressed
            if event.type == pygame.QUIT:
                exit()
        mouse = pygame.mouse.get_pos()
        xpos = int(math.ceil(mouse[0]) / 64.0)
        ypos = int(math.ceil(mouse[1]) / 64.0)

        isoutofbounds = False
        temp = pBoard
        try:
            if not temp[ypos][xpos]:
                self.screen.blit(self.playerselector,
                                 [xpos * 64 + 5, (ypos * 64) + 10])
        except:
            isoutofbounds = True
            pass
        if not isoutofbounds:
            alreadyplaced = pBoard[ypos][xpos]
        else:
            alreadyplaced = False

        if pygame.mouse.get_pressed()[0] and not alreadyplaced \
                and not isoutofbounds:
            opponent = self.teams[ypos + (xpos * 7)]
            connection.Send({'action': "selectPlayer",
                             'player0': self.teamname,
                             'player1': opponent})
            self.playerselect = True
        pygame.display.flip()

        """ Screen Refresh Method """
        # self.Send({"action": "getPlayers",
        # "teamname": self.teamname})

        sleep(0.01)

    def drawPlayerboard(self):
        self.screen.blit(self.leaderboard, (389, 0))
        myfont20 = pygame.font.SysFont(None, 20)
        i = 0
        for x in range(6):
            for y in range(7):
                if i < len(self.teams):
                    self.screen.blit(self.activeplayer, [x * 64 + 5, y * 65 + 5 + 5])
                    playername = myfont20.render(self.teams[i], 1, (255, 255, 255))
                    self.screen.blit(playername, [x * 64 + 5 + 15, y * 65 + 5 + 45])
                else:
                    self.screen.blit(self.inactiveplayer, [x * 64 + 5, y * 65 + 5 + 5])

                i += 1

    def drawUpgrade(self):
        self.screen.fill(0)
        self.screen.blit(self.gameroom, (0, 0))
        myfont = pygame.font.SysFont(None, 32)
        msg = myfont.render("Please upgrade your version", 1, (255, 255, 255))

        self.screen.blit(msg, (135, 250))


    #################################
    ###     tournament Mode       ###
    #################################

    def tWaiting(self):
        self.screen.fill(0)
        self.drawTwaiting()
        pygame.display.flip()
        connection.Pump()
        self.Pump()


    def drawTwaiting(self):
        self.screen.blit(self.gameroom, (0, 0))
        myfont = pygame.font.SysFont(None, 64)
        label = myfont.render("Waiting", 1, (255, 255, 255))
        self.screen.blit(label, (200, 200))


    #################################
    ###         Cram Game         ###
    #################################

    def drawBoard(self):
        for x in range(5):
            for y in range(5):
                self.screen.blit(self.normallineh, [(x) * 64 + 5, (y) * 64])
                self.screen.blit(self.normallinev, [(x) * 64, (y) * 64 + 5])
        for edge in range(5):
            self.screen.blit(self.normallineh, [edge * 64 + 5, 5 * 64])
            self.screen.blit(self.normallinev, [5 * 64, edge * 64 + 5])
        for x in range(6):
            for y in range(6):
                self.screen.blit(self.separators, [x * 64, y * 64])
        for x in range(5):
            for y in range(5):
                if self.owner[y][x] == 0:
                    self.screen.blit(self.selector, [(x * 64 + 5), (y) * 64 + 5])
                if self.owner[y][x] == 1:
                    self.screen.blit(self.blueplayer, [(x * 64 + 5), (y) * 64 + 5])
                if self.owner[y][x] == 2:
                    self.screen.blit(self.masterblock, [(x * 64 + 5), (y) * 64 + 5])

    def drawHUD(self):
        self.screen.blit(self.score_panel, [0, 325])
        myfont = pygame.font.SysFont(None, 32)
        label = myfont.render("Your Turn:", 1, (255, 255, 255))

        self.screen.blit(label, (10, 325))
        self.screen.blit(self.greenindicator if self.turn else self.redindicator, (130, 340))

        myfont64 = pygame.font.SysFont(None, 64)
        myfont20 = pygame.font.SysFont(None, 20)

        scoreme = myfont64.render(str(self.me), 1, (255, 255, 255))
        scoreother = myfont64.render(str(self.opponent), 1, (255, 255, 255))
        scoretextme = myfont20.render("You", 1, (255, 255, 255))
        scoretextother = myfont20.render("Other Player", 1, (255, 255, 255))
        countdown = myfont64.render(str(self.timer), 1, (255, 255, 255))
        counttext = myfont20.render("Timer:", 1, (255, 255, 255))

        self.screen.blit(scoretextme, (10, 370))
        self.screen.blit(scoreme, (10, 380))
        self.screen.blit(scoretextother, (240, 370))
        self.screen.blit(scoreother, (270, 380))
        self.screen.blit(counttext, (10, 425))
        self.screen.blit(countdown, (50, 425))

    def drawLeaders(self):
        self.screen.blit(self.leaderboard, (389, 0))
        myfont64 = pygame.font.SysFont(None, 64)
        myfont20 = pygame.font.SysFont(None, 20)

        if len(self.leaders) != 0:
            for y in range(len(self.leaders)):
                name = myfont64.render(self.leaders[y][0], 1, (255, 255, 255))
                label = myfont20.render("Score:", 1, (255, 255, 255))
                scr = myfont64.render(str(self.leaders[y][1]), 1, (255, 255, 255))
                self.screen.blit(name, [415, y * 64 + 25])
                self.screen.blit(label, [415 + 64, y * 64 + 5 + 20])
                self. screen.blit(scr, [500, y * 63 + 5 + 20])


    def update(self):
        if self.playagain:
            self.mainMenu()
            self.playagain = False
        """
        " Main game loop
        """
        connection.Pump()
        self.Pump()

        self.screen.fill(0)
        self.drawBoard()
        self.drawHUD()
        self.drawLeaders()
        pygame.display.flip()

        for event in pygame.event.get():
            # quit id the button is pressed
            if event.type == pygame.QUIT:
                exit()

        if self.turn:
            if not self.ismoveready:
                self.ismoveready = True
                thread.start_new_thread(self.makeMove, ())

        pygame.display.flip()

        if self.isgameover:
            self.gameOver()

        if self.endsession:
            return 1

    def makeMove(self):
        result = self.pyva.Move()
        y1 = result[0]
        x1 = result[1]
        y2 = result[2]
        x2 = result[3]
        connection.Send(
            {"action": "place", "x1": x1, "y1": y1, "x2": x2, "y2": y2,
             "playerID": self.playerID, "turn": self.turn,
             "gameID": self.gameID})

    def gameOver(self):
        self.screen.blit(
            self.gameover if not self.didiwin else self.winningscreen, (0, 0))
        pygame.display.flip()
        sleep(7)
        if not self.tournamentMode:
            self.screen.blit(self.playagainimg, (150, 400))
            while 1:
                for event in pygame.event.get():
                    if event.type == pygame.QUIT:
                        exit()
                    elif pygame.mouse.get_pressed()[0]:
                        self.playagain = True
                        connection.Send({"action": "restart",
                                         "playerID": self.playerID,
                                         "gameID": self.gameID})
                        self.reset()
                        break
                if self.playagain:
                    break
                pygame.display.flip()
        else:
            self.roUnd += 1
            rouNd, didw, score = self.roUnd, self.didiwin, self.me
            connection.Send({"action": "restart",
                             "playerID": self.playerID,
                             "gameID": self.gameID})
            self.reset()
            sleep(7)
            connection.Send({"action": "tournament",
                             "teamname": self.teamname,
                             "round": self.roUnd,
                             "WorL": self.didiwin,
                             "score": self.me})


    def reset(self):
        """
        " Reinitialize game settings
        """
        self.board = [[False for x in range(5)] for y in range(5)]
        self.owner = [[None for x in range(5)] for y in range(5)]
        self.turn = False
        self.playerID = 0
        self.gameID = None

        self.me = 0
        self.opponent = 0
        self.didiwin = False
        self.isgameover = False
        self.ismoveready = False

        if not self.tournamentMode:
            self.tournamentMode = False
            self.roUnd = 0

            self.playagain = False
            self.selected = False
            self.playerselect = False
            self.tBegin = False
            self.begingame = False
            self.mainMenu()
        else:
            self.selected = True
            self.playerselect = True
            self.begingame = False
            self.tBegin = False
            self.tWaiting()
            self.timer = 10


    #######################################
    ### Network event/message callbacks ###
    #######################################

    def Network_connected(self, data):
        print "Connected to Crunch-Platform. Enjoy!"

    def Network_error(self, data):
        print 'error:', data['error'][1]
        connection.Close()

    def Network_disconnected(self, data):
        print "Disconnected from Cram-Platform"
        exit()

    def Network_upgrade(self, data):
        self.drawUpgrade()
        pygame.display.flip()
        sleep(10)
        connection.Close()

    ######################################
    ### Room event/messages callbacks  ###
    ######################################
    def Network_retpList(self, data):
        self.selected = True
        self.teams = data['players']

    ######################################
    ###   tournament event/messages    ###
    ######################################
    def Network_enter(self, data):
        self.selected = True
        self.playerselect = True
        self.tWaiting()

    def Network_tournydone(self, data):
        self.tournamentMode = False
        connection.Send({"action": "restart",
                         "playerID": self.playerID,
                         "gameID": self.gameID})
        self.reset()

    def Network_tstats(self, data):
        self.leaders = data["leaders"]

    ######################################
    ###  Cram game specific callbacks  ###
    ######################################

    def Network_botplay(self, data):
        """
        " Starts a game with the server bots
        """
        self.selected = True
        self.startgame = True
        self.playerID = data['playerID']
        self.gameID = data['gameID']

    def Network_startgame(self, data):
        """
        " Starts a game with another client
        """
        self.selected = True
        self.playerselect = True
        self.tBegin = True
        self.begingame = True
        self.playerID = data['playerID']
        self.gameID = data['gameID']
        print "New Game Started >> Game: " \
              + str(self.gameID)

    def Network_validmove(self, data):
        self.ismoveready = False
        x1 = data['x1']
        y1 = data['y1']
        x2 = data['x2']
        y2 = data['y2']
        playerID = data['playerID']
        if playerID != 2:
            self.turn = data['turn']
            self.opponent = data["opscore"]
            self.me = data["mscore"]
        self.board[y1][x1] = True
        self.board[y2][x2] = True
        self.owner[y1][x1] = playerID
        self.owner[y2][x2] = playerID
        if self.turn:
            previousMove = [x1, y1, x2, y2]
            self.pyva.opMove(previousMove)
        self.pyva.updateBoard(self.owner)
        sleep(2)

    def Network_invalidmove(self, data):
        self.ismoveready = False
        print "invalid move"

    def Network_gameover(self, data):
        self.tournamentMode = data["tourn"]
        self.me = data["mscore"]
        self.opponent = data["opscore"]
        if self.me > self.opponent:
            self.didiwin = True
        self.isgameover = True

    def Network_timer(self, data):
        self.timer = data['time']

    def Network_timesup(self, data):
        self.turn = data['turn']


    #####################################
    ###       Game graphics           ###
    #####################################

    def initGraphics(self):
        """
        " Start menu graphics
        """
        #self.home = pygame.image.load("./images/home.png")
        self.gameroom = pygame.image.load("./images/GameRoom.png")
        self.greenplayer = pygame.image.load("./images/greenplayer.png")
        self.blueplayer = pygame.image.load("./images/blueplayer.png")
        self.inactiveplayer = pygame.image.load("./images/inactiveplayer.png")
        self.activeplayer = pygame.image.load("./images/activeplayer.png")
        self.playerselector = pygame.image.load("./images/playerselector.png")
        self.botimg = pygame.image.load("./images/bot.png")
        self.playagainimg = pygame.image.load("./images/playagain.png")
        self.leaderboard = pygame.image.load("./images/leaderboard.png")
        self.tournament = pygame.image.load("./images/tournamentimg.png")

        """
        " Game Graphics
        """
        self.normallinev = pygame.image.load("./images/normalline.png")
        self.normallineh = pygame.transform.rotate(pygame.image.load("./images/normalline.png"), -90)
        self.separators = pygame.image.load("./images/separators.png")
        self.redindicator = pygame.image.load("./images/redindicator.png")
        self.greenindicator = pygame.image.load("./images/greenindicator.png")
        self.greenplayer = pygame.image.load("./images/greenplayer.png")
        self.blueplayer = pygame.image.load("./images/blueplayer.png")
        self.winningscreen = pygame.image.load("./images/youwin.png")
        self.gameover = pygame.image.load("./images/gameover.png")
        self.score_panel = pygame.image.load("./images/nscore_panel.png")
        self.selector = pygame.image.load("./images/selector.png")
        self.masterblock = pygame.image.load("./images/masterblock.png")


cramClient = CramClient("localhost", 27000)
while 1:
    if cramClient.update() == 1:
        break
cramClient.gameOver()



