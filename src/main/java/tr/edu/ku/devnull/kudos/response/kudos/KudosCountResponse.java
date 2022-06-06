package tr.edu.ku.devnull.kudos.response.kudos;

import lombok.Builder;
import lombok.Data;

import java.math.BigInteger;

@Data
@Builder
public class KudosCountResponse {
    BigInteger totalCount;
}
