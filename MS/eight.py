

import random
from pynput import keyboard

class Escape(Exception):
    pass

class Eight:
    class KeyboardListener:
        def listen(self):
            res = self.pressed
            self.pressed = ''
            if res == '':
                return False
            return res

        def on_press(self, keyp):
            if keyp == keyboard.Key.esc:
                raise Escape(keyp)
            
            try:
                self.pressed = keyp.char
            except:
                self.pressed = keyp.name

        def __init__(self):
            self.pressed = ''
            self.listener = keyboard.Listener(on_press=self.on_press)
            try:
                self.listener.start()
            except Escape:
                print('ending game')
            # with keyboard.Listener(on_press=self.on_press) as self.listener:
            #     try:
            #         self.listener.join()
            #     except self.Escape:
            #         print(f'ending game')        
        def __str__(self):
            return f'keyboard listener'
        # Keyboard Listener ------------

    class Tile:
        def __init__(self, tiles):
            self.tiles = tiles
        
        def row(self, n):
            return self.tiles[n]
        def row1(self):
            return self.row(1)
        def row2(self):
            return self.row(2)
        def row3(self):
            return self.row(3)
        def col(self, n):
            res = []
            for row in [self.row(k) for k in len(self.tiles)]:
                res.append(row[n])
        def col1(self):
            return self.col(1)
        def col2(self):
            return self.col(2)
        def col3(self):
            return self.col(3)
        
        def randomize(self):
            rowlen = len(self.tiles[0])
            exp = [i for row in self.tiles for i in row]
            random.shuffle(exp)
            for row in self.tiles:
                for i in range(rowlen):
                    row[i] = exp.pop()
            
        def locate(self):
            for i, row in enumerate(self.tiles):
                for j, num in enumerate(row):
                    if num == 0:
                        return [i, j]
        
        def swap(self, fr, to):
            a = self.tiles[fr[0]][fr[1]]
            b = self.tiles[to[0]][to[1]]
            self.tiles[fr[0]][fr[1]] = b
            self.tiles[to[0]][to[1]] = a

        def __str__(self):
            retstr = ''
            for rownum in range(len(self.tiles)): 
                for tile in self.row(rownum):
                    retstr += f'{tile }'
                retstr += '\n'
            return retstr
        def __eq__(self, other):
            return self.tiles == other.tiles
        # Tile ----------------

    # EIGHT --------------   
    def __init__(self):
        self.memo = []
        self.goal = self.Tile([
            [1,2,3],
            [8,0,4],
            [7,6,5]
        ])
        self.curr = self.Tile([
            [1,2,3],
            [8,0,4],
            [7,6,5]
        ])
        self.moves = 0
    
    def move(self, move):
        loc = self.curr.locate()
        moved = False
        if move in ['left', 'a']:
            if loc[1] == 0:
                return False
            self.curr.swap(loc, [loc[0], loc[1]-1])
            moved = True
        if move in ['right', 'd']:
            if loc[1] == len(self.curr.tiles[0])-1:
                return False
            self.curr.swap(loc, [loc[0], loc[1]+1])
            moved = True
        if move in ['up', 'w']:
            if loc[0] == 0:
                return False
            self.curr.swap(loc, [loc[0]-1, loc[1]])
            moved = True
        if move in ['down','s']:
            if loc[0] == len(self.curr.tiles)-1:
                return False
            self.curr.swap(loc, [loc[0]+1, loc[1]])
            moved = True
        if moved:
            self.moves += 1
            print('0 location', self.curr.locate())
            print('\n'.join(map(str, [row for row in self.curr.tiles])))
    
    def win(self):
        return self.curr == self.goal

    def randomize(self):
        print('before----')
        print('0 location', self.curr.locate())
        print('\n'.join(map(str, [row for row in self.curr.tiles])))

        print('after----')
        self.curr.randomize()
        print('0 location', self.curr.locate())
        print('\n'.join(map(str, [row for row in self.curr.tiles])))

    def start(self):
        self.con = self.KeyboardListener()
        movements = ['up','left','right','down','w','s','a','d']
        while True:
            move = self.con.listen()
            if move in movements:
                self.move(move)
                if self.win():
                    print(f'win {self.moves} moves')

    def print_memo(self):
        pass 

    def print_curr(self):
        print(self.curr)

game = Eight()
game.randomize()
game.start()