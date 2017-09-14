package com.github.maxxkrakoa;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * Created by mv on 2017-09-14.
 */
@JsonPropertyOrder({"TYPE", "CONTENT", "PRIORITY", "INDENT", "AUTHOR", "RESPONSIBLE", "DATE", "DATE_LANG" ,"TIMEZONE"})
public class TodoistLineItemJava {
    public String TYPE;
    public String CONTENT;
    public String PRIORITY;
    public String INDENT;
    public String AUTHOR;
    public String RESPONSIBLE;
    public String DATE;
    public String DATE_LANG;
    public String TIMEZONE;
}
