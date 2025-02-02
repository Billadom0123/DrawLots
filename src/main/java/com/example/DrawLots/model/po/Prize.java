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
    private int lotsId;
    private String type;
    private String name;
    private int number;
    private String pictureUrl;
    private String description;
}
