package com.bikkaditdurgesh.payload;

import lombok.Data;

@Data
public class JwtAuthRequest {

	private String username;// यहां हमने email को ही username consider किया है

	private String password;

}
