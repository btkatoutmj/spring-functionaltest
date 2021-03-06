/*
 * Copyright(c) 2014-2017 NTT Corporation.
 */
package jp.co.ntt.fw.spring.functionaltest.domain.repository.athn;

import jp.co.ntt.fw.spring.functionaltest.domain.model.Customer;

public interface CustomerRepository {

    Customer findOneByName(String name);

}
