package pl.devoxx.prezentatr.feed

import groovy.transform.Canonical
import org.springframework.cloud.context.config.annotation.RefreshScope
import org.springframework.stereotype.Component

@Component
@RefreshScope
class FeedRepository {

    final private Set<Process> processes = Collections.synchronizedSet(new HashSet<>())

    private Integer bottles = 0

    void addModifyProcess(String id, ProcessState newState) {
        Process p;
        if ((p = processes.find { it.id == id })) {
            p.state = newState
        } else {
            processes.add(new Process(id, newState))
        }
    }

    void setBottles(String id, Integer bottles) {
        this.bottles = bottles

        addModifyProcess(id, ProcessState.DONE)
    }

    String showStatuses() {
        return "DOJRZEWATR: ${countFor(ProcessState.DOJRZEWATR)}\n" +
                "BUTELKATR: ${countFor(ProcessState.BUTELKATR)}"
    }

    Integer countFor(ProcessState state) {
        return processes.count { it.state == state }
    }

    Integer getBottles() {
        return bottles
    }
}

@Canonical
class Process {
    String id
    ProcessState state
}

enum ProcessState {
    DOJRZEWATR,
    BUTELKATR,
    DONE
}