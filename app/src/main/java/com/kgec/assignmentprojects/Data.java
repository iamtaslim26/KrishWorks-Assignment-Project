package com.kgec.assignmentprojects;

import fr.nelaupe.spreadsheetlib.SpreadSheetCell;

public class Data {
    @SpreadSheetCell(name = "ID", size = 100, position = 1)
    private String  id;

    @SpreadSheetCell(name = "StudentName", size = 300, position = 2)
    private String studentName;

    @SpreadSheetCell(name = "Marks", size = 300, position = 3)
    private String marks;

    private String status;

//
//    @SpreadSheetCell(name = "Status", size = 300, position = 4)
//    private String status;


//    public Data(Integer id, String studentName, String marks) {
//        this.id = id;
//        this.studentName = studentName;
//        this.marks = marks;
//    }

    public Data(String id, String studentName, String marks,String status) {
        this.id=id;
        this.studentName = studentName;
        this.marks = marks;
        this.status=status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String  getId() {
        return id;
    }

    public void setId(String  id) {
        this.id = id;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getMarks() {
        return marks;
    }

    public void setMarks(String marks) {
        this.marks = marks;
    }
}
