# toodledo2todoist
Simple conversion from Toodledo CSV Export to Todoist Import Template (CSV).

Will only support a small set of Toodledo features, namely `task name`, 
`folder`, `due date`, `repeat`, `note`, `tag`, `priority`.

## TODO
Add support for
- task name 
- folder
- due date
- repeat
- note
- tag
- priority

## Notes
Toodledo priorities go from -1 to 3. 0 is "Low" and is the default value. 3 is "Top".

Todoist priorities go from 4 (which is the default) to 1 (which is the top priority).

Todoist entries can be composed of multiple consecutive lines ending with a line with all empty entries.