package com.sistemasactivos.apirest.account.interfaces;

import com.sistemasactivos.apirest.account.model.Base;
import java.io.Serializable;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface BaseController <E extends Base, ID extends Serializable>{
    
    public ResponseEntity<?> getAllRecord();
    public ResponseEntity<?> getAllRecord(Pageable pageable);
    public ResponseEntity<?> getRecordById(@PathVariable ID id);
    public ResponseEntity<?> save(@RequestBody E entity);
    public ResponseEntity<?> update(@PathVariable ID id, @RequestBody E entity);
    public ResponseEntity<?> delete(@PathVariable ID id);

    
}
