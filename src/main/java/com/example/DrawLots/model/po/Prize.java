package com.example.DrawLots.model.po;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Prize {

    private Integer id;

    private Integer lotsId;

    private Integer type;//奖项

    private String name;

    private int number;//该奖项有几个人中奖

    private String pictureUrl;

    private String description;

}
