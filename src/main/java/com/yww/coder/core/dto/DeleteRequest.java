package com.yww.coder.core.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class DeleteRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
}
