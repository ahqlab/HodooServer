package net.octacomm.sample.domain;

import lombok.Data;
import net.octacomm.sample.message.ResultMessage;


@Data
public class ResultMessageGroup {

    public Object domain;

    public ResultMessage resultMessage;
    
}
