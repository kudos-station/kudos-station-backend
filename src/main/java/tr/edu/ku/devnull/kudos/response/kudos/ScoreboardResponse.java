package tr.edu.ku.devnull.kudos.response.kudos;

import lombok.Builder;
import lombok.Data;

import java.math.BigInteger;
import java.util.List;

@Data
@Builder
public class ScoreboardResponse {
    String username;
    BigInteger totalCount;
}
