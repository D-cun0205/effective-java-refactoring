package com.refectoring.effectivejava.ref._11_primitive_obsession._02_replace_type_code_with_subclasses.indirect_inheritance;

import com.refectoring.effectivejava.ref._11_primitive_obsession._02_replace_type_code_with_subclasses.direct_inheritance.Employee;

public class Engineer extends EmployeeType {
    @Override
    public String toString() {
        return "engineer";
    }
}
