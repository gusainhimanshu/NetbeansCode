/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.himanshu.test.cachebuilder;

import com.google.common.base.MoreObjects;
/**
 *
 * @author hgusain
 */
class Employee {

    String name;
    String dept;
    String emplD;

    public Employee(String name, String dept, String emplD) {
        this.name = name;
        this.dept = dept;
        this.emplD = emplD;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getEmplD() {
        return emplD;
    }

    public void setEmplD(String emplD) {
        this.emplD = emplD;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(Employee.class)
                .add("Name", name)
                .add("Department", dept)
                .add("Emp Id", emplD).toString();
    }
}
