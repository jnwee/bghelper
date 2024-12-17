import React from "react";

const LinkButton = ({ href, iconClass, label }) => {
  return (
    <a href={href} className="link-button">
      {iconClass && <i className={`icon ${iconClass}`}></i>}
      {label}
    </a>
  );
};

export default LinkButton;
