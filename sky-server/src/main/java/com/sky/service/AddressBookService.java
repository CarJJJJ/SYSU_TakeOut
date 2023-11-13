package com.sky.service;

import com.sky.entity.AddressBook;
import org.apache.ibatis.annotations.Insert;

import java.util.List;

public interface AddressBookService {

    public void save(AddressBook addressBook);

    List<AddressBook> list(AddressBook addressBook);

    void update(AddressBook addressBook);

    AddressBook getById(Long id);

    void deleteById(Long id);

    void setDefault(AddressBook addressBook);
}
