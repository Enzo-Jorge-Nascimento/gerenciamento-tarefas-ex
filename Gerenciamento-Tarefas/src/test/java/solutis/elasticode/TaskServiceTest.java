package solutis.elasticode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import solutis.elasticode.dtos.TaskMapping;
import solutis.elasticode.dtos.TaskDto;
import solutis.elasticode.entities.Task;
import solutis.elasticode.enums.Status;
import solutis.elasticode.repositories.TaskRepository;
import solutis.elasticode.services.TaskService;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository repository;

    @Mock
    private TaskMapping mapping;

    @InjectMocks
    private TaskService service;

    private TaskDto dto;
    private Task entity;

    @BeforeEach
    void setUp() {
        dto = new TaskDto("Task Teste", "Isso é um teste", Status.EM_ANDAMENTO);

        entity = new Task("Task Teste", "Isso é um teste", Status.EM_ANDAMENTO);
    }

    @Test
    void createTask_validTask_returnsSavedTask() {
        when((mapping.toEntity(dto))).thenReturn(entity);
        when(repository.save(any(Task.class))).thenReturn(entity);
        when(mapping.toDto(entity)).thenReturn(dto);

        TaskDto savedEntity = mapping.toDto(service.criarTask(dto));

        assertNotNull(savedEntity);
        assertEquals("Task Teste", savedEntity.getTitulo());
        verify(repository, times(1)).save(entity);
        verify(mapping, times(1)).toEntity(dto);
        verify(mapping, times(1)).toDto(entity);
    }

    @Test
    void getAllTasks_returnsTaskList() {
        when(repository.findAll()).thenReturn(Arrays.asList(entity));

        var tasks = service.listarTasks();

        assertEquals(1, tasks.size());
        assertEquals("Task Teste", tasks.get(0).getTitulo());
        verify(repository, times(1)).findAll();
    }

    @Test
    void getTaskById_validId_returnsTask() {
        when(repository.findById(1)).thenReturn(Optional.of(entity));
        when(mapping.toDto(entity)).thenReturn(dto);

        TaskDto foundEntity = mapping.toDto(service.buscarTask(1));

        assertNotNull(foundEntity);
        assertEquals("Task Teste", foundEntity.getTitulo());
        verify(repository, times(1)).findById(1);
        verify(mapping, times(1)).toDto(entity);
    }

}
