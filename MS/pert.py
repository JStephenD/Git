
from math import floor, ceil
def round(n):
    if n - floor(n) >= 0.5:
        return ceil(n)
    return floor(n)

class Pert:
    class Activity:
        code = ''

        a = None # optimistic
        b = None # pessimistic
        m = None # most probable time
        predecessors_codes = [] # ['name', 'name'] of predecessor
        predecessors_objs = [] # [type(Activity)] of predecessor

        et = None
        es = None
        ef = None

        ls = None
        lf = None

        slack = None

        def __init__(self, a, m, b, code='', predecessors_codes=[]):
            self.predecessors_codes = predecessors_codes
            self.predecessors_objs = []
            self.a = a
            self.b = b
            self.m = m
            self.code = code

        def solve_e(self):
            self._et()
            self._es()
            self._ef()
        
        def solve_l(self, lf):
            if self.lf is None:
                self.lf = lf
            elif lf < self.lf:
                self.lf = lf
            self.ls = self.lf - self.et
            for pred in self.predecessors_objs:
                pred.solve_l(self.ls)
        
        def solve_s(self):
            self.slack = self.lf - self.ef

        def _et(self):
            self.et =  round( (self.a + (4 * self.m) + self.b) / 6 )

        def _es(self):
            if len(self.predecessors_objs) == 0:
                self.es = 0
            else:
                self.es = max([p.ef for p in self.predecessors_objs])
            
        def _ef(self):
            self.ef = self.et + self.es
        
        def addPredecessor(self, other):
            self.predecessors_objs.append(other)

        def __str__(self):
            return f'{self.code}\t{self.et}\t{self.es}\t{self.ef}\t{self.ls}\t{self.lf}\t{self.slack}'

    activities = None
    ends = None
    ends_objs = None

    def __init__(self):
        self.activities = []
        self.ends = []
        self.ends_objs = []

    def solve(self):
        for activity in self.activities: # add predecessor objects to the activities
            for code in activity.predecessors_codes:
                predecessor = self.find_activity(code)
                if predecessor:
                    activity.addPredecessor(predecessor)
            activity.solve_e() # solve et es ef
        
        self.gen_endsobjs()
        for endobj in self.ends_objs:
            endobj.solve_l(endobj.ef)
        
        for activity in self.activities:
            activity.solve_s()

    def gen_endsobjs(self):
        for end in self.ends:
            self.ends_objs.append(self.find_activity(end))

    def find_activity(self, code):
        for activity in self.activities:
            if activity.code == code:
                return activity
                break
        return False

    def add(self, a, m, b, code, predecessors=[]):
        new = self.Activity(a,m,b,code,predecessors)
        self.activities.append(new)
        self._update(end = new)

    def _update(self, end):
        # ends pointer
        if end:
            if len(end.predecessors_codes) > 0:
                for code in end.predecessors_codes: # delete ends that is a predecessor of new end
                    if code in self.ends:
                        self.ends.remove(code)
                self.ends.append(end.code) # add new activity to ends if it has predecessors
        
        for end in self.ends: # for end in ends check if it is part of ends.predecessors
            for activity in self.activities:
                if end in activity.predecessors_codes:
                    self.ends.remove(end)


obj = Pert()
obj.add(1,10,1,'A')
obj.add(1,8,1,'B')
obj.add(1,21,1,'C',['A'])
obj.add(1,7,1,'D',['A'])
obj.add(1,16,1,'E',['C'])
obj.add(1,10,1,'F',['D'])
obj.add(1,16,1,'G',['B'])
obj.add(0,6,0,'H',['F','E'])
obj.add(0,27,0,'I',['G'])
# -----------------------------
# obj.add(2,3,4,'A')
# obj.add(3,7,7,'B')
# obj.add(1,2,3,'C',['A','B'])
# obj.add(1,3,15,'D',['C'])
# obj.add(2,3,9,'E',['C'])
# obj.add(0,4,4,'F',['D','E'])
# obj.add(6,8,15,'G',['E'])
# obj.add(2,2,7,'H',['F', 'G'])

obj.solve()

print(f'{obj.ends=}')
print('-----------------')

print('Code\tET\tES\tEF\tLS\tLF\tSlack')
for act in obj.activities:
    print(act)
