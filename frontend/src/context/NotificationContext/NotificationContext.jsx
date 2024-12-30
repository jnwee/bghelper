"use client";

import React, { createContext, useContext, useState } from "react";

import "./notifications.css";
import Button from "@/components/Button";

const NotificationContext = createContext();

export const useNotification = () => useContext(NotificationContext);

export const NotificationProvider = ({ children }) => {
  const [notifications, setNotifications] = useState([]);

  const showNotification = (message, type = "success", duration = 8000) => {
    const id = Date.now();
    const newNotification = { id, message, type };

    setNotifications((prev) => [...prev, newNotification]);

    // Auto-dismiss
    setTimeout(() => {
      setNotifications((prev) => prev.filter((n) => n.id !== id));
    }, duration);
  };

  const dismissNotification = (id) => {
    setNotifications((prev) => prev.filter((n) => n.id !== id));
  };

  return (
    <NotificationContext.Provider value={{ showNotification }}>
      {children}

      {/* Render Notifications */}
      <div className="notification-container">
        {notifications.map((notification) => (
          <div
            key={notification.id}
            className={`notification show custom-bg-${notification.type}`}
            onClick={() => dismissNotification(notification.id)}
          >
            <p className="notification-content">{notification.message}</p>
            <Button
              variant="close"
              onClick={() => dismissNotification(notification.id)}
            />
          </div>
        ))}
      </div>
    </NotificationContext.Provider>
  );
};
