# toodledo2todoist
Simple conversion from Toodledo CSV Export to Todoist Import Template (CSV).

In the "it works for me" state.

Only supports a small set of Toodledo features, namely `task name`, 
`folder`, `due date`, `repeat`, `note`, `tag`, `priority`.

**Import Template functionality requires Todoist Premium**

## How to use
First export all the active todos from Toodledo and place them in a file.

Create a directory for the output files. 

Then run:

`./gradlew run -Dexec.args="/path/to/toodledo.csv /path/to/outputdir/"`

There should now be a CSV file in the output directory for each folder in Toodledo.
Each of these files can be imported into Todoist.

## Notes
Toodledo priorities go from -1 to 3. 0 is "Low" and is the default value. 3 is "Top".

Todoist priorities go from 4 (which is the default) to 1 (which is the top priority).

Todoist entries can be composed of multiple consecutive lines ending with a line with all empty entries.

## TODO
In Todoist it is impossible to create recurring events that start in the past. So if there are any
of these present while converting a warning will be displayed.
