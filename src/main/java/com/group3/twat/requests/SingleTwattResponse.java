package com.group3.twat.requests;

import com.group3.twat.twatt.model.TwattPublicDTO;
import org.springframework.data.domain.Page;

public record SingleTwattResponse(
        TwattPublicDTO parentTwatt,
        TwattPublicDTO mainTwatt,
        Page<TwattPublicDTO> comments
) {
}
