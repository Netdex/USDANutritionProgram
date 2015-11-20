package parser.parsables;

import parser.util.DoublyLinkedList;

/**
 * Created by Netdex on 11/20/2015.
 */
public class FootnoteGroup {
    private DoublyLinkedList<Footnote> footnotes = new DoublyLinkedList<>();

    public FootnoteGroup(){}

    public void addFootnote(Footnote fn){
        footnotes.add(fn);
    }
    public Footnote[] getFootnotes(){
        return footnotes.toArray(Footnote.SAMPLE);
    }
}
