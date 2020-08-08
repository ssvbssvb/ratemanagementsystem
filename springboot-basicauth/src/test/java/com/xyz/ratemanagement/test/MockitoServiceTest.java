package com.xyz.ratemanagement.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.xyz.ratemanagement.dao.RatesRepository;
import com.xyz.ratemanagement.model.Rate;
import com.xyz.ratemanagement.service.RateManagentServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class MockitoServiceTest {

	@InjectMocks
	private RateManagentServiceImpl rateManagementService;

	@Mock
	private RatesRepository rateRepository;

	private List<Rate> ratesList;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);

		ratesList = Arrays.asList(new Rate("testdesc1", new Date(), new Date(), new Double(12.5)),
				new Rate("testdesc2", new Date(), new Date(), new Double(22.5)),
				new Rate("testdesc3", new Date(), new Date(), new Double(32.5)));
	}

	@Test
	public void testServiceSearchByRateId() {
		given(rateRepository.findByRateId(any(Long.class))).willReturn(ratesList.get(0));
		Rate rate = rateManagementService.findByRateId(1L);
		assertNotNull(rate);
		assertEquals(rate, ratesList.get(0));

	}

	@Test
	public void testAddRate() {
		given(rateRepository.save(any(Rate.class))).willReturn(ratesList.get(0));
		Rate rate = rateManagementService.save(ratesList.get(0));
		assertNotNull(rate);
		assertEquals(rate, ratesList.get(0));

	}

	@Test
	public void testdeleteRate() {
		rateManagementService.delete(any(Long.class));
		verify(rateRepository).deleteById(any(Long.class));
	}

	@Test(expected = Exception.class)
	public void testAddRateWithException() {
		given(rateRepository.save(any(Rate.class))).willThrow(Exception.class);
		rateManagementService.save(ratesList.get(0));
	}

}