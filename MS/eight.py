

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
    
    def move(self, action):
        pass

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
        while True:
            action = self.con.listen()
            if action:
                print(action)

    def print_memo(self):
        pass 

    def print_curr(self):
        print(self.curr)

game = Eight()
game.randomize()
# game.start()