import React, { Component } from "react";

class ProjectTask extends Component {
  render() {
    const { project_task_prop } = this.props;

    let priorityClass, priorityString;

    if (project_task_prop.priority === 1) {
      priorityClass = "bg-danger text-light";
      priorityString = "HIGH";
    }

    if (project_task_prop.priority === 2) {
      priorityClass = "bg-warning text-light";
      priorityString = "MEDIUM";
    }

    if (project_task_prop.priority === 3) {
      priorityClass = "bg-info text-light";
      priorityString = "LOW";
    }
    return (
      <div className="card mb-1 bg-light">
        <div className={`card-header text-primary ${priorityClass}`}>
          ID: {project_task_prop.projectSequence} - Priority: {priorityString}
        </div>
        <div className="card-body bg-light">
          <h5 className="card-title">{project_task_prop.summary}</h5>
          <p className="card-text text-truncate ">
            {project_task_prop.acceptanceCriteria}
          </p>
          <a href="#" className="btn btn-primary">
            View / Update
          </a>

          <button className="btn btn-danger ml-4">Delete</button>
        </div>
      </div>
    );
  }
}

export default ProjectTask;
