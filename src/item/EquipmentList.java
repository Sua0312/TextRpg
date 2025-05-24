package item;

import util.Status;

public enum EquipmentList {
    NULL_POINTER_EXCEPTION_SET("Null Pointer Exception Set",  new Status(4, 1.0, 0.5, 0.2, 0.7, 0.3, 1.0)),
    CLASS_NOT_FOUND_EXCEPTION_SET("Class Not Found Exception Set", new Status( 3.4, 0.8, 0.8, 0.1, 0.5, 0.4, 0.9)),
    OUT_OF_MEMORY_ERROR_SET("Out Of Memory Error Set",  new Status(2.8, 1.2, 0.6, 0.1, 0.4, 0.6, 1.3)),
    STACK_OVERFLOW_ERROR_SET("Stack Overflow Error Set",  new Status(1.3, 0.5, 1.6, 0.3, 0.9, 0.2, 1.5)),
    ILLEGAL_ARGUMENT_EXCEPTION_SET("Illegal Argument Exception Set", new Status( 0.0, 0.0, 0.0, 0.0, 2.0, 0.0, 50.0));

    private final String baseName;
    private final String name;
    private final Status status;

    /**
     * 생성자: 장비의 능력치를 설정
     */
    EquipmentList(String name, Status status) {
        baseName = name;
        this.name = name;
        this.status = status;
    }

    public String getName() {
        return baseName;
    }
    public Status getStatus() {
        return status;
    }
}