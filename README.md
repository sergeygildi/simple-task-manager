## Overview


**How does a person usually shop?** If he needs more than one product,
but several, it is very likely that he will first make a list,
not to forget anything. You can do it anywhere: on a piece of paper,
in the application for notes or, for example, in a message to yourself in the messenger.
Now imagine that this is not a list of products, but full-fledged cases.
And not some simple things like "wash the dishes" or "call grandma"
and complex ones - for example, "organize a big family holiday" or "buy an apartment."
Each of these tasks can be divided into several stages with their own nuances and deadlines.
And if not one person, but a whole team works on their implementation,
then the organization of the process will become even more difficult.

---
## Task Manager

### Task types

The TaskManager has the following task life stages:
 
    NEW - the task has just been created, but its execution has not yet begun. 
    IN_PROGRESS - work is being done on the task.
    DONE - task completed.

Each task has its own unique identification number by which it can be found.

#### Tasks can be of three types: 
    - Regular 
    - Epics 
    - Subtasks

For each subtask, it is known within which epic it is performed.
Each epic knows what subtasks it includes.
Completing all subtasks of an epic counts as completing the epic.

---
## Manager
In addition to classes for describing tasks, you need to implement a class for the manager object.
It will run at the start of the program and manage all tasks.

### It implements the following functions:

    - Ability to store tasks of all types. 
    
### Methods:
    - Get a list of all tasks. 
    - Getting a list of all epics. 
    - Getting a list of all subtasks of a specific epic. 
    - Getting a task of any type by ID. 
    - Adding a new issue, epic and subtask. The object itself must be passed as a parameter. 
    - Update a task of any type by ID. The new version of the object is passed as a parameter. 
    - Delete previously added tasks - all and by ID.

### Status management is carried out according to the following rule:

   - The manager himself does not choose the status for the task. Information about it comes
   manager along with information about the task itself.
        
#### For epics:

- if the epic has no subtasks or all of them have the NEW status, then the status should be `NEW`.
- if all subtasks have the DONE status, then the epic is considered completed - with the `DONE` status.
- in all other cases the status should be `IN_PROGRESS`.

---
## Data update
When updating, you can consider that a new object is fed into the input, which should completely replace the old one.
For example, a method to update an epic can take an epic as input
`public void updateEpic (model.Epic epic)`. If you store epics in a HashMap where IDs are the keys,
then update is writing a new epic `epic.put(epic.getId(), epic))`.

## Update task status
The phrase "information comes along with information on the task" means that there is no separate method,
which would deal only with updating the status of the task. Instead, the task status is updated together
with a complete update of the task.

## Epic update
It can be seen that the epic does not manage its status on its own. This means:
1. The user should not be able to change the status of an epic on their own.
2. When the status of any sub-task in an epic changes, you need to check if
   that the status of the epic will change accordingly. In this case, changing the epic status can
   and not happen if, for example, it still has open tasks.

