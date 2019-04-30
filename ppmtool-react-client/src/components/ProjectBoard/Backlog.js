import React, { Component } from "react";
import ProjectTask from "./ProjectTasks/ProjectTask";

class Backlog extends Component {
  render() {
    const { project_tasks_prop } = this.props;
    const tasks = project_tasks_prop.map(projectTask => (
      <ProjectTask key={projectTask.id} project_task_prop={projectTask} />
    ));

    let todoItems = [];
    let inProgressItems = [];
    let doneItems = [];

    tasks.forEach(ele => {
      let status = ele.props.project_task_prop.status;
      if (status === "TO_DO") todoItems.push(ele);
      if (status === "IN_PROGRESS") inProgressItems.push(ele);
      if (status === "DONE") doneItems.push(ele);
    });

    return (
      <div className="container">
        <div className="row">
          <div className="col-md-4">
            <div className="card text-center mb-2">
              <div className="card-header bg-secondary text-white">
                <h3>TO DO</h3>
              </div>
            </div>
            {todoItems}
          </div>
          <div className="col-md-4">
            <div className="card text-center mb-2">
              <div className="card-header bg-primary text-white">
                <h3>In Progress</h3>
              </div>
            </div>
            {inProgressItems}
          </div>
          <div className="col-md-4">
            <div className="card text-center mb-2">
              <div className="card-header bg-success text-white">
                <h3>Done</h3>
              </div>
            </div>
            {doneItems}
          </div>
        </div>
      </div>
    );
  }
}

export default Backlog;
