package net.octacomm.sample.domain.param;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.octacomm.sample.domain.DomainParam;

@Getter
@Setter
@ToString(callSuper = true)
public class NoticeParam extends DomainParam {
	
	private String searchField;

	private String searchWord;
}
