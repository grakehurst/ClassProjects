# Gavin Akehurst
# Comparative Languages 
# Project 7: Student Records
# 5 December 2024


# imports
import sys
import os

def main():

    # file to read from 
    # get the path name of the current directory as a string
    currentDirectory = os.path.dirname(__file__)

    # add the name of the text file to the directory
    filePath = os.path.join(currentDirectory, "studentRecordsIn.txt")

    # check if the file does not exist and exit if true
    if not os.path.exists(filePath):
           print("Error: File does not Exist")
           sys.exit(1)


    # file to write to
    newFilePath = os.path.join(currentDirectory, "studentRecordsOut.txt")

    # counter for starter output in the file
    fileCounter = 0

    # starter ouput
    print("\nStudent Records\n===============\n")

    # start by opening the file to write to 
    with open(newFilePath, "w") as fileOut:
          
          # at the beginning of the file, print the header
          if fileCounter == 0:
               fileCounter = 1 # dont ever loop again
               fileOut.write("Student Records\n===============\n\n")

          # then open the file to read from
          with open(filePath, "r") as file:

               # loop through each line since each line is a student
               for line in file:
                    # counter to decide what the variable is
                    counter = 0

                    # check if the line is empty
                    # if it is a blank line, skip this iteration so it does not make a blank student
                    if line.strip() == "": # strip removes whitespaces
                         continue

                    # variables to store
                    firstName = ""   # student's first name
                    lastName = ""    # student's last name
                    uid = 0          # student's user id
                    level = "" # undergrad or grad student
                    classes = []     # list of student's classes

                    # get each word, set the variables
                    for word in line.split():

                         # switch based off of which word is next
                         match counter:
                              case 0: # uid
                                   uid = word
                              case 1: # first name
                                   firstName = word
                              case 2: # last name
                                   lastName = word
                              case 3: # grad or undergrad
                                   level = word
                              case _: # everything else is a class
                                   classes.append(word)
                         counter += 1

                    # create a student with these variables
                    newStudent = Student(uid, firstName, lastName, level)
                    for i in classes:
                         newStudent.add_class(i)
                    
                    # output the student to the console  
                    print(newStudent)
                    newStudent.resetIndex() # reset the index variable so we can print it again

                    # write this student to the file
                    # change standout out to the file, 
                    # print it again, and change standard out back
                    originalOut = sys.stdout
                    sys.stdout = fileOut
                    print(newStudent)
                    sys.stdout = originalOut                  

# student class
class Student:
     
    # initialize main variables
    # create an empty set of classes
    def __init__(self, uid, firstName, lastName, level):
          self.lastName = lastName
          self.firstName = firstName
          self.uid = str(uid)
          self.level = level
          self.classes = []

          # throw all variables in the init into an array for the iterator
          self.varsArray = [self.uid, self.firstName, self.lastName, self.level]
          self.index = 0 # index to iterate through class variables


    # add a class to this student's class list
    def add_class(self, addedClass):
         self.classes.append(addedClass)
        
        # add to the total array
         self.varsArray.append(addedClass)
         

    # iterator 
    def __iter__(self):
         return self
    
    # next method for the iterator
    # returns a string that has a label and a the next variable in the class
    def __next__(self):

         # check if everthing has been iterated
         if self.index >= len(self.varsArray):
              raise StopIteration
         
         # otherwise return the next item with its label, and increase the index counter
         else:
              # string to be added to for formatting output
              returnString = ""

              # get the next item in the object
              nextVar = self.varsArray[self.index]

              # decide what to label each variable based on the index 
              match self.index:
                    case 0: # uid
                         returnString = "UID: " + nextVar + "\n"

                    case 1: # first name
                         returnString = "First Name: " + nextVar + "\n"

                    case 2: # last name
                         returnString = "Last Name: " + nextVar + "\n"

                    case 3: # grad or undergrad
                         returnString = "Level: " + nextVar + "\n"

                    case _: # everything else is a class, so return each one individually
                         # if the index is 4, output the classes label first
                         if self.index == 4:
                              returnString = "CLASSES:\n"
                         
                         # then add the next class
                         returnString += nextVar + "\n"

              # increase the index counter
              self.index += 1

              return returnString

         
    # method convert this object to a string 
    # its an empty string joined by each var in self
    # the for loop in join iterates each object
    # the periods separate each student
    def __str__(self):
          return "".join(var for var in self) + ".............\n" + ".............\n"
    
    def resetIndex(self):
         self.index = 0
    
# end student



# test output
# student1 = Student(888, 'Lisa', 'Simpson', 'Grad')
# student1.add_class('CS6500')
# student1.add_class('CEG7200')
# student1.add_class('CEG8100')
# print("student1: ")
# print(student1)




if __name__ == '__main__':
    main()