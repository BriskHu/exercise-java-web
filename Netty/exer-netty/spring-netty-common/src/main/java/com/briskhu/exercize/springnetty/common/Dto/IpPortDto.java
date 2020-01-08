package com.briskhu.exercize.springnetty.common.Dto;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * <p/>
 *
 * @author Brisk Hu
 * created on 2019-12-09
 **/
@Data
@ToString
public class IpPortDto implements Serializable{
    private static final long serialVersionUID = 5121183944780203027L;
    private String ip;
    private int port;
}