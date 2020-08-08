package com.xyz.ratemanagement.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.xyz.ratemanagement.dao.RatesRepository;
import com.xyz.ratemanagement.model.Rate;

@RunWith(SpringRunner.class)
@DataJpaTest
public class RatesRepositoryTest {
 
    @Autowired
    private TestEntityManager entityManager;
    
    @Autowired
    private RatesRepository ratesRepository;
    
    @Test
    public void testSaveRates() {
    	Rate rate = new Rate("testdata", new Date(),new Date(),new Double(12.5));
		Long id = entityManager.persistAndGetId(rate, Long.class);
		assertNotNull(id);
	}
    
    @Test
    public void testGetRates() {
    	Rate rate = new Rate("testdata2", new Date(),new Date(),new Double(22.5));
		Long id = entityManager.persistAndGetId(rate, Long.class);
		Rate dbRates = ratesRepository.findByRateId(id);
		assertEquals(dbRates.getRateDescription(), "testdata2");
		}
    
    @Test
    public void testdeleteRates() {
    	Rate rate = new Rate("testdata3", new Date(),new Date(),new Double(12.5));
		Long id = entityManager.persistAndGetId(rate, Long.class);
		ratesRepository.delete(rate);
		assertNull(entityManager.find(Rate.class,id));
		}
    
    @Test
    public void testupdateRates() {
    	Rate rate = new Rate("testdata4", new Date(),new Date(),new Double(12.5));
		Long id = entityManager.persistAndGetId(rate, Long.class);
		Rate dbRates = ratesRepository.findByRateId(id);
		dbRates.setRateDescription("testdata44");
		entityManager.persist(dbRates);
		Rate updatedRates = ratesRepository.findByRateId(id);
		assertEquals(updatedRates.getRateDescription(), "testdata44");
		
		}
 
  
}