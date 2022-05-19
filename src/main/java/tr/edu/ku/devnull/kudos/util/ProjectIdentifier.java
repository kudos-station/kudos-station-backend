package tr.edu.ku.devnull.kudos.util;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProjectIdentifier implements Serializable {
    private Long projectID;
    private Long departmentID;
}
