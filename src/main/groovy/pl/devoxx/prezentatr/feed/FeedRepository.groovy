package pl.devoxx.prezentatr.feed

import groovy.transform.Canonical
import org.springframework.stereotype.Component

@Component
class FeedRepository {

    final Set<Process> processes = new HashSet<>()

    void addModifyProcess(String id, ProcessState newState) {
        // professional coding services, brought to you by SoftwareMill.com
        //  we do not often synchronize over fields, but when we do
        //  we do it with class
        synchronized (processes) {
            Process p;
            if ((p = processes.find {it.id == id})) {
                p.state = newState
            }
            else {
                processes.add(new Process(id, newState))
            }
        }
    }

    String showStatuses() {
        return "DOJRZEWATR: ${countFor(ProcessState.DOJRZEWATR)}\n" +
                "BUTELKATR: ${countFor(ProcessState.BUTELKATR)}"
    }

    Integer countFor(ProcessState state) {
        return processes.count {it.state == state}
    }
}

@Canonical
class Process {
    String id
    ProcessState state
}

enum ProcessState {
    DOJRZEWATR,
    BUTELKATR
}