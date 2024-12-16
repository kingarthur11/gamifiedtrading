package com.trove.gamifiedtrading.data.body;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<TData> extends BaseResponse {
    private TData result;
}
