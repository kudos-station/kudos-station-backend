package tr.edu.ku.devnull.kudos.util;

import lombok.Data;

import java.io.Serializable;

@Data
public class WorksInIdentifier implements Serializable {
    private Long userID;
    private Long departmentID;
}
