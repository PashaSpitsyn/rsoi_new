package rsoi;

import bean.Bill;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import rsoi.persistence.BillRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BillControllerTest {

    @InjectMocks
    private BillController controller;

    @Mock
    private BillRepository billRepository;

    private Bill bill = new Bill();

    @Before
    public void setUp() throws Exception {
        when(billRepository.findAll(any(PageRequest.class))).thenReturn(new PageImpl<>(Collections.singletonList(bill)));
        when(billRepository.findById(anyLong())).thenReturn(Optional.of(bill));
    }

    @Test
    public void testList() {
        List<Bill> list = controller.list(0, 10);
        Assertions.assertThat(list).hasSize(1);
    }

    @Test
    public void testUpdate() {
        controller.update(12L, bill);
        verify(billRepository, times(1)).save(any(Bill.class));
    }

    @Test
    public void testGetById() {
        Optional<Bill> bill = controller.get(42L);
        Assertions.assertThat(bill).isNotEmpty();
    }
}