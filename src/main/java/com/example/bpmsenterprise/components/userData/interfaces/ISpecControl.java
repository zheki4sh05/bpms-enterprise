package com.example.bpmsenterprise.components.userData.interfaces;

import com.example.bpmsenterprise.components.userData.DTO.ChangeSpecDTO;
import com.example.bpmsenterprise.components.userData.DTO.SpecDTO;

import java.util.List;

public interface ISpecControl {
    SpecDTO create(Integer company, String name);

    SpecDTO update(Integer id, String name, Integer count);

    Integer delete(Integer id);

    List<SpecDTO> fetch(String name);

    List<SpecDTO> change(ChangeSpecDTO changeSpecDTO);
}
