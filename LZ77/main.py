class Tag(object):
    def __init__(self, position, length, symbol):
        self.position = position
        self.length = length
        self.symbol = symbol


def Encoding(word, size):
    position = 0
    length = 0
    wordResult = []
    SearchBuffer = ""
    lookaheadBuffer = word
    match = ""
    idx = 0
    list = []

    for i in range(len(lookaheadBuffer)):
        match += lookaheadBuffer[0]
        if len(SearchBuffer) > size and size != 0:
            idx += len(SearchBuffer) - size
            SearchBuffer = SearchBuffer[len(SearchBuffer) - size:]
            length = 0

        if match not in SearchBuffer:
            if position > 7 and length == 1:
                position = 0
                length = 0
            wordResult.append("<{},{},{}".format(position, length, lookaheadBuffer[0]))
            SearchBuffer += match
            lookaheadBuffer = lookaheadBuffer[1:]
            match = ""
            list.clear()
            length = 0
        else:
            if (i == len(word) - 1):
                if position > 7 and length == 1:
                    position = 0
                    length = 0
                wordResult.append("<{},{},{}".format(position, length, lookaheadBuffer[0]))
                break
            else:
                lookaheadBuffer = lookaheadBuffer[1:]
            list.append(i)
            if (size != 0):
                position = list[0] - word.index(match, idx, size + idx)
            else:
                position = list[0] - word.index(match)

            length += 1
            continue
    return wordResult


def Decoding(Tags, noOfTags):
    SearchBuffer = ""
    for i in range(noOfTags):
        Start = len(SearchBuffer) - Tags[i].position
        End = Start + Tags[i].length
        SearchBuffer += SearchBuffer[Start:End] + Tags[i].symbol
    return SearchBuffer


print("Basel-Youssef(20200111) \t Seif El-Dein-Yasser(20200241)\n")
print("What would you like to do? (1:Encoding \t 2:Decoding)")
choice = int(input())
if choice == 1:
    print("Please Enter The Encoding Sequence")
    sequence = str(input())
    print("Do You want a FixedSize (SearchSize and LookAheadSize) \n (YES | NO)")
    DoI = str(input())
    DoI.upper()
    if DoI == "YES":
        print("Enter SearchWindow and AheadWindow in order")
        SearchSize = int(input())
        AheadSize = len(sequence) - SearchSize
    else:
        SearchSize = 0
        AheadSize = 0

    resultt = Encoding(sequence, SearchSize)
    print("Result is ", resultt)
elif choice == 2:
    print("Enter number of Tags")
    noOfTags = int(input())
    Tags = []
    for i in range(noOfTags):
        print("Enter ", (i + 1), " Tag:")
        p = int(input("\t\tEnter Position: "))
        l = int(input("\t\tEnter Length: \t"))
        c = input("\t\tEnter Character:")
        Tag_Obj = Tag(p, l, c)
        Tags.append(Tag_Obj)

    answer = Decoding(Tags, noOfTags)
    print("Result is ", answer)
else:
    exit(0)



