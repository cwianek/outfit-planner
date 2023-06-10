FROM nginx:1.21-alpine

COPY services-proxy/nginx.conf /etc/nginx/nginx.conf

# Copy the build output from the previous stage to the Nginx document root
#COPY mnt/c/Projekty/outfit-planner-mf/dist/apps/products /usr/share/nginx/html

# Expose port 80
EXPOSE 80

# Start Nginx
CMD ["nginx", "-g", "daemon off;"]