import ply.lex as lex

class BGDLexer(object):
    
    reserved = {
        'while' : 'WHILE',
        'for' : 'FOR',
        'if' : 'IF',
        'else' : 'ELSE',
        'elseif' : 'ELSEIF',
        'continue' : 'CONTINUE',
        'return' : 'RETURN',
        'break' : 'BREAK',
        'NIL' : 'NIL',
        'PIECE' : 'PIECE',
        'BOARD' : 'BOARD',
        'PLAYER' : 'PLAYER',
        'RULE' : 'RULE',
        'FUNCTION' : 'FUNCTION',
        'action' : 'ACTION',
        'def' : 'DEF',
        'OR' : 'OR',
        'AND' : 'AND',
        'NOT' : 'NOT',
        'to' : 'TO',
        'in' : 'IN'
        }
    tokens = [
        'NUMBER',
        'BOOLEAN',
        'STRING',
        'INDENT',
        'DEDENT',
        'ID',
        'COMMENT',
        'NEWLINE',
        'WHITESPACE'
        ] + list(reserved.values())

    literals = [ '+', '-', '*', '/', '%', ':', '=', ',', '>', '<',\
                 '.', '(', ')', '[', ']']


    def t_NUMBER(self, t):
        r'[+-]?[0-9]+(\.[0-9]+)?'
        return t

    def t_BOOLEAN(self, t):
        r'YES|NO'
        return t


    def t_STRING(self, t):
        r'\'.*\''
        t.value = t.value[1:-1]
        return t

    def t_ID(self, t):
        r'[a-zA-Z_][a-zA-Z_0-9]*'
        t.type = self.reserved.get(t.value,'ID')
        return t

    t_ignore_COMMENT = r'@@.*$'

    t_ignore = r'\n[ \t]*\n'
    
    t_WHITESPACE = r'\n[ ]*'           

    def t_error(self, t):
        print "Illegal character '%s'" % t.value[0]
        t.lexer.skip(1)

    def build(self,**kwargs): 
        self.lexer = lex.lex(module=self, **kwargs)
        self.indents = [0]  # indentation stack
        self.tokens = []    # token queue

    def token(self):
        if self.tokens:
            return self.tokens.pop(0)
        # loop until we find a valid token
        while 1:
            # grab the next from first stage
            token = self.lexer.token()

            # we only care about whitespace
            if not token or token.type != 'WHITESPACE':
                return token

            # check for new indent/dedent
            whitespace = token.value[1:]  # strip \n
            change = self._calc_indent(whitespace)
            if change:
                break

        # indentation change
        if change == 1:
            token.type = 'INDENT'
            return token
        # buffer any additional DEDENTs
        while change:
            self.tokens.append(copy.copy(token))
            change += 1
        return token

    def _calc_indent(self, whitespace):
        "returns a number representing indents added or removed"
        n = len(whitespace) # number of spaces
        indents = self.indents # stack of space numbers
        if n > indents[-1]:
            indents.append(n)
            return 1

        # we are at the same level
        if n == indents[-1]:
            return 0

        # dedent one or more times
        i = 0
        while n < indents[-1]:
            indents.pop()
            if n > indents[-1]:
                raise SyntaxError("wrong indentation level")
            i -= 1
        return i
        

    def tok_str(self, data):
        self.lexer.input(data)
        tok_str = ""
        while True:
            tok = self.lexer.token()
            if not tok:
                break
            tok_str += str(tok) + "\n"
        print tok_str
        return tok_str
    
if __name__ == '__main__':
    lexer = BGDLexer()
    lexer.build()
    print 'Input a text'
    line = raw_input()
    lexer.tok_str(line)