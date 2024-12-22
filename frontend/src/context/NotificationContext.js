// context/NotificationContext.js
"use client";

import React, { createContext, useContext, useState } from "react";
import "@/app/css/notifications.css";

const NotificationContext = createContext();

// Hook for accessing notifications
export const useNotification = () => useContext(NotificationContext);

export const NotificationProvider = ({ children }) => {
  const [notifications, setNotifications] = useState([]);

  // Show a notification with dynamic message and type (success, danger, etc.)
  const showNotification = (message, type = "success", duration = 4000) => {
    const id = Date.now();
    const newNotification = { id, message, type };

    setNotifications((prev) => [...prev, newNotification]);

    // Auto-dismiss after duration
    setTimeout(() => {
      setNotifications((prev) => prev.filter((n) => n.id !== id));
    }, duration);
  };

  // Remove a notification manually
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
            className={`notification toast show bg-${notification.type}`}
          >
            <div className="toast-header">
              <strong>{notification.type.toUpperCase()}</strong>
              <button
                className="btn-close"
                onClick={() => dismissNotification(notification.id)}
              ></button>
            </div>
            <div className="toast-body">{notification.message}</div>
          </div>
        ))}
      </div>
    </NotificationContext.Provider>
  );
};
