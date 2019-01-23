package rsoi;

import bean.Car;
import bean.Part;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import rsoi.persistence.PartRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PartControllerTest {

    @InjectMocks
    private PartController controller;

    @Mock
    private PartRepository repository;

    private Part part = new Part();

    @Before
    public void setUp() throws Exception {
        when(repository.findAll(any(PageRequest.class))).thenReturn(new PageImpl<>(Collections.singletonList(part)));
        when(repository.findById(anyLong())).thenReturn(Optional.of(part));
    }

    @Test
    public void testList() {
        List<Part> list = controller.list(0, 10);
        Assertions.assertThat(list).hasSize(1);
    }

    @Test
    public void testUpdate() {
        controller.update(12L, part);
        verify(repository, times(1)).save(any(Part.class));
    }

    @Test
    public void testGetById() {
        Optional<Part> bill = controller.get(42L);
        Assertions.assertThat(bill).isNotEmpty();
    }


}